package org.test.mjsip;

import java.io.IOException;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.message.Message;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.SipProviderListener;
import org.zoolu.sip.provider.SipStack;
import org.zoolu.tools.Archive;

import local.media.AudioClipPlayer;
import local.ua.UserAgent;
import local.ua.UserAgentListener;
import local.ua.UserAgentProfile;

public class MyTestApplication {

	public static void main(String[] args) {
		System.out.println("Application start ...");

		if (!SipStack.isInit()) {
			SipStack.init();
		}
		// SipStack.debug_level = SipStack.LOG_LEVEL_UA;
		SipStack.debug_level = 8;
		SipProvider sipProvider = new SipProvider("127.0.0.1", 4242);

		UserAgentProfile profile = new UserAgentProfile();
		profile.audio = true;
		profile.audio_port = 3000;
		profile.hangup_time = -1;
		profile.username = "jvoicexml";
		profile.keepalive_time = 8000;
		profile.bin_rat = "rat";
		profile.bin_vic = "vic";
		// profile.use_rat = true;
		// profile.send_tone = true;
		final ExtendedUserAgent userAgent = new ExtendedUserAgent(sipProvider, profile,
				new UserAgentListener() {

					@Override
					public void onUaCallAccepted(UserAgent arg0) {
						System.out.println("onUaCallAccepted ...");
					}

					@Override
					public void onUaCallCancelled(UserAgent arg0) {
						System.out.println("onUaCallCancelled ...");
					}

					@Override
					public void onUaCallClosed(UserAgent arg0) {
						System.out.println("onUaCallClosed ...");
					}

					@Override
					public void onUaCallFailed(UserAgent arg0) {
						System.out.println("onUaCallFailed ...");
					}

					@Override
					public void onUaCallIncoming(UserAgent arg0,
							NameAddress arg1, NameAddress arg2) {
						System.out.println("onUaCallIncoming ...");
						System.out.println("from " + arg2.toString());
						System.out.println("to " + arg1.toString());

						arg0.accept();
						System.out.println("Call accepted ...");

						// AudioClipPlayer clip_on = new AudioClipPlayer(
						// Archive.getAudioInputStream(Archive.getJarURL(
						// jar_file,CLIP_ON)),
						// null);
						// arg0.
					}

					@Override
					public void onUaCallRinging(UserAgent arg0) {
						System.out.println("onUaCallRinging ...");
					}

					@Override
					public void onUaCallTrasferred(UserAgent arg0) {
						System.out.println("onUaCallTrasferred ...");
					}
				});

		sipProvider.addSipProviderListener(new SipProviderListener() {

			@Override
			public void onReceivedMessage(SipProvider sip_provider,
					Message message) {

				System.out.println("onReceivedMessage ... message "
						+ message.getMethodId().toString());
			}
		});
		sipProvider.addSipProviderPromisqueListener(new SipProviderListener() {

			@Override
			public void onReceivedMessage(SipProvider sip_provider,
					Message message) {

				if (message.isInfo()) {
					System.out
							.println("Promisque onReceivedMessage ... message "
									+ message.getMethodId().toString()
									+ "\n body = " + message.getBody());

					if (message.getBody().indexOf("Signal=1") >= 0) {
						userAgent.playAudioFile("H:/Projects/AeroIVR/Archive/Investigation/WAV/play.wav");
					}
					else {
//						userAgent.playAudioFile("H:/Projects/AeroIVR/Archive/Investigation/WAV/sample.wav");
					}
				}
			}
		});

		userAgent.listen();
		System.out.println("Application ended ...");
	}

}
