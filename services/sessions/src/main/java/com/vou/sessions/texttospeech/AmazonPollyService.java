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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class AmazonPollyService {
	private static final String SAMPLE = """
		<speak>
		    <p>
		        <s>The correct answer is <break time="1s"/> <emphasis level="strong"><prosody pitch="+60%" rate="fast">D</prosody></emphasis> </s>
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

//    public static void main(String args[]) throws Exception {
	//create the test class
//        AmazonPollyService service = new AmazonPollyService();
	//get the audio stream
//        InputStream speechStream = service.synthesize(SAMPLE, OutputFormat.Mp3);
	
	// Play audio
//        service.playAudio(speechStream);
	// Upload to S3
//        String s3Key = "answers/D.mp3"; // Đường dẫn file trong bucket
//        service.uploadToS3(speechStream, s3Key);
//
//        // Generate S3 URL
//        String fileUrl = service.getPresignedUrl(s3Key);
//        System.out.println("File URL: " + fileUrl);
//    }
	
	public InputStream synthesize(String text, OutputFormat format) {
		SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
			.withText(text).withVoiceId(voice.getId())
			.withOutputFormat(format)
			.withTextType(TextType.Ssml);
		
		SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
		
		return synthRes.getAudioStream();
	}
	
	public String uploadToS3(InputStream inputStream, String key) {
		s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, null));
		return s3Client.getUrl(bucketName, key).toString();
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
		ssmlBuilder.append("<s>Question number ").append(quizNumber).append("<break time=\"200ms\"/></s>");
		ssmlBuilder.append("<s>").append(escapeSSMLText(quizQuestion.getQuestion())).append("<break " +
			"time=\"500ms\"/></s>");
		
		List<String> answers = new ArrayList<>();
		int correctAnswerIndex = quizQuestion.getCorrectAnswerIndex();
		answers.add("");
		answers.addAll(quizQuestion.getIncorrectAnswers());
		answers.set(0, answers.get(correctAnswerIndex));
		answers.set(correctAnswerIndex, quizQuestion.getCorrectAnswer());
		
		for (int i = 0; i < answers.size(); i++) {
			if (i == 3) ssmlBuilder.append("And ");
			ssmlBuilder.append("<s>").append((char) ('A' + i)).append(".<break time=\"200ms\"/> ").append(escapeSSMLText(answers.get(i))).append("<break time=\"500ms\"/></s>");
		}
		
		ssmlBuilder.append("</p></speak>");
		
		return ssmlBuilder.toString();
	}
	
	private String escapeSSMLText(String text) {
		if (text == null) {
			return "";
		}
		return text.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\"", "&quot;")
			.replace("'", "&apos;");
	}
}
