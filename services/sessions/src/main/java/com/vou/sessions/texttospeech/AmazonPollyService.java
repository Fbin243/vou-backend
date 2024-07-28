package com.vou.sessions.texttospeech;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vou.sessions.engine.quizgame.QuizQuestion;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class AmazonPollyService {
    private static final String SAMPLE = """
            <speak>
                <p>
                    <s>Question number 1<break time="500ms"/></s>
                    <s>The human heart has how many chambers?<break time="1s"/></s>
                    <s>A.<break time="200ms"/> 2<break time="500ms"/></s>
                    <s>B.<break time="200ms"/> 6<break time="500ms"/></s>
                    <s>C.<break time="200ms"/> 3<break time="500ms"/></s>
                    <s>And D.<break time="200ms"/> 4<break time="500ms"/></s>
                </p>
            </speak>
            """;
    private final AmazonPolly polly;
    private final Voice voice;
    private final AmazonS3 s3Client;
    private final String bucketName = "voubucket";

    public AmazonPollyService() {
        polly = AmazonPollyClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
        s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

        // Synchronously ask Amazon Polly to describe available TTS voices.
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        voice = describeVoicesResult.getVoices().stream().filter(p -> p.getName().equals("Brian")).findFirst().get();
    }

    public static void main(String args[]) throws Exception {
        //create the test class
        AmazonPollyService service = new AmazonPollyService();
        //get the audio stream
        InputStream speechStream = service.synthesize(SAMPLE, OutputFormat.Mp3);

        // Play audio
        service.playAudio(speechStream);
//
//        // Upload to S3
//        String s3Key = "questions/sample.mp3"; // Đường dẫn file trong bucket
//        service.uploadToS3(speechStream, s3Key);
//
//        // Generate S3 URL
//        String fileUrl = service.getPresignedUrl(s3Key);
//        System.out.println("File URL: " + fileUrl);
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
                .withText(text).withVoiceId(voice.getId())
                .withOutputFormat(format)
                .withTextType(TextType.Ssml);

        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public void uploadToS3(InputStream inputStream, String key) {
        s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, null));
    }

    public String getPresignedUrl(String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(com.amazonaws.HttpMethod.GET)
                .withExpiration(new java.util.Date(System.currentTimeMillis() + 3600 * 1000)); // URL hợp lệ trong 1 giờ

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public void playAudio(InputStream speechStream) throws Exception {
        //create an MP3 player
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });

        // play it!
        player.play();
    }

    public String convertQuizQuestionToSSML(QuizQuestion quizQuestion, int quizNumber) {
        StringBuilder ssmlBuilder = new StringBuilder("<speak>");
        ssmlBuilder.append("<p>");
        ssmlBuilder.append("<s>Question number ").append(quizNumber).append("<break time=\"500ms\"/></s>");
        ssmlBuilder.append("<s>").append(quizQuestion.getQuestion()).append("<break time=\"1s\"/></s>");

        List<String> answers = new ArrayList<>();
        answers.add(quizQuestion.getCorrectAnswer());
        answers.addAll(quizQuestion.getIncorrectAnswers());

        for (int i = 0; i < answers.size(); i++) {
            if (i == 3) ssmlBuilder.append("And ");
            ssmlBuilder.append("<s>").append((char) ('A' + i)).append(".<break time=\"200ms\"/> ").append(answers.get(i)).append("<break time=\"500ms\"/></s>");
        }

        ssmlBuilder.append("</p></speak>");

        return ssmlBuilder.toString();
    }
}