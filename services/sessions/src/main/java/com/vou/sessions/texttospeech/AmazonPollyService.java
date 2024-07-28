package com.vou.sessions.texttospeech;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.IOException;
import java.io.InputStream;

public class AmazonPollyService {
    private static final String SAMPLE = "Congratulations. You have successfully built this working demo of Amazon Polly in Java. Have fun building voice enabled apps with Amazon Polly (that's me!), and alwayslook at the AWS website for tips and tricks on using Amazon Polly and other great services from AWS";
    private final AmazonPolly polly;
    private final Voice voice;

    public AmazonPollyService() {
        polly = AmazonPollyClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();

        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

        // Synchronously ask Amazon Polly to describe available TTS voices.
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        voice = describeVoicesResult.getVoices().stream().filter(p -> p.getName().equals("Brian")).findFirst().get();
    }

    public static void main(String args[]) throws Exception {
        //create the test class
        AmazonPollyService helloWorld = new AmazonPollyService();
        //get the audio stream
        InputStream speechStream = helloWorld.synthesize(SAMPLE, OutputFormat.Mp3);

        //create an MP3 player
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
                System.out.println(SAMPLE);
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });


        // play it!
        player.play();

    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
                .withText(text).withVoiceId(voice.getId())
                .withOutputFormat(format);

        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }
}