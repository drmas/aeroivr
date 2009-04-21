package org.test.mjsip;

import java.util.Enumeration;

import org.zoolu.sdp.MediaDescriptor;
import org.zoolu.sdp.MediaField;
import org.zoolu.sdp.SessionDescriptor;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.SipStack;
import org.zoolu.tools.Archive;
import org.zoolu.tools.Log;
import org.zoolu.tools.LogLevel;
import org.zoolu.tools.Parser;

import local.media.AudioClipPlayer;
import local.ua.JAudioLauncher;
import local.ua.MediaLauncher;
import local.ua.RATLauncher;
import local.ua.UserAgent;
import local.ua.UserAgentListener;
import local.ua.UserAgentProfile;
import local.ua.VICLauncher;

public class ExtendedUserAgent extends UserAgent {
	
	Log log;
	

	public ExtendedUserAgent(SipProvider sip_provider,
			UserAgentProfile user_profile, UserAgentListener listener) {
		super(sip_provider, user_profile, listener);
	}

	protected void launchMediaApplication() {
		// exit if the Media Application is already running
		if (audio_app != null || video_app != null) {
			printLog("DEBUG: media application is already running",
					LogLevel.HIGH);
			return;
		}
		SessionDescriptor local_sdp = new SessionDescriptor(call
				.getLocalSessionDescriptor());
		String local_media_address = (new Parser(local_sdp.getConnection()
				.toString())).skipString().skipString().getString();
		int local_audio_port = 0;
		int local_video_port = 0;
		// parse local sdp
		for (Enumeration e = local_sdp.getMediaDescriptors().elements(); e
				.hasMoreElements();) {
			MediaField media = ((MediaDescriptor) e.nextElement()).getMedia();
			if (media.getMedia().equals("audio"))
				local_audio_port = media.getPort();
			if (media.getMedia().equals("video"))
				local_video_port = media.getPort();
		}
		// parse remote sdp
		SessionDescriptor remote_sdp = new SessionDescriptor(call
				.getRemoteSessionDescriptor());
		String remote_media_address = (new Parser(remote_sdp.getConnection()
				.toString())).skipString().skipString().getString();
		int remote_audio_port = 0;
		int remote_video_port = 0;
		for (Enumeration e = remote_sdp.getMediaDescriptors().elements(); e
				.hasMoreElements();) {
			MediaField media = ((MediaDescriptor) e.nextElement()).getMedia();
			if (media.getMedia().equals("audio"))
				remote_audio_port = media.getPort();
			if (media.getMedia().equals("video"))
				remote_video_port = media.getPort();
		}

		// select the media direction (send_only, recv_ony, fullduplex)
		int dir = 0;
		if (user_profile.recv_only)
			dir = -1;
		else if (user_profile.send_only)
			dir = 1;

		if (user_profile.audio && local_audio_port != 0
				&& remote_audio_port != 0) { // create an audio_app and start it
			if (user_profile.use_rat) {
				audio_app = new RATLauncher(user_profile.bin_rat,
						local_audio_port, remote_media_address,
						remote_audio_port, log);
			} else if (user_profile.use_jmf) { // try to use JMF audio app
				try {
					Class myclass = Class.forName("local.ua.JMFAudioLauncher");
					Class[] parameter_types = { java.lang.Integer.TYPE,
							Class.forName("java.lang.String"),
							java.lang.Integer.TYPE, java.lang.Integer.TYPE,
							Class.forName("org.zoolu.tools.Log") };
					Object[] parameters = { new Integer(local_audio_port),
							remote_media_address,
							new Integer(remote_audio_port), new Integer(dir),
							log };
					java.lang.reflect.Constructor constructor = myclass
							.getConstructor(parameter_types);
					audio_app = (MediaLauncher) constructor
							.newInstance(parameters);
				} catch (Exception e) {
					printException(e, LogLevel.HIGH);
					printLog("Error trying to create the JMFAudioLauncher",
							LogLevel.HIGH);
				}
			}
			// else
			if (audio_app == null) { // for testing..
				// audio_app=new
				// JAudioLauncher(local_audio_port,remote_media_address
				// ,remote_audio_port,dir,log);
				audio_app = new FileAudioLauncher(local_audio_port,
						remote_media_address, remote_audio_port, dir, 
						user_profile.audio_sample_rate,
						user_profile.audio_sample_size,
						user_profile.audio_frame_size, log);
			}
			audio_app.startMedia();
		}
		if (user_profile.video && local_video_port != 0
				&& remote_video_port != 0) { // create a video_app and start it
			if (user_profile.use_vic) {
				video_app = new VICLauncher(user_profile.bin_vic,
						local_video_port, remote_media_address,
						remote_video_port, log);
			} else if (user_profile.use_jmf) { // try to use JMF video app
				try {
					Class myclass = Class.forName("local.ua.JMFVideoLauncher");
					Class[] parameter_types = { java.lang.Integer.TYPE,
							Class.forName("java.lang.String"),
							java.lang.Integer.TYPE, java.lang.Integer.TYPE,
							Class.forName("org.zoolu.tools.Log") };
					Object[] parameters = { new Integer(local_video_port),
							remote_media_address,
							new Integer(remote_video_port), new Integer(dir),
							log };
					java.lang.reflect.Constructor constructor = myclass
							.getConstructor(parameter_types);
					video_app = (MediaLauncher) constructor
							.newInstance(parameters);
				} catch (Exception e) {
					printException(e, LogLevel.HIGH);
					printLog("Error trying to create the JMFVideoLauncher",
							LogLevel.HIGH);
				}
			}
			// else
			if (video_app == null) {
				printLog(
						"No external video application nor JMF has been provided: Video not started",
						LogLevel.HIGH);
				return;
			}
			video_app.startMedia();
		}
	}

	void printLog(String str) {
		printLog(str, LogLevel.HIGH);
	}

	void printLog(String str, int level) {
		if (log != null)
			log.println("UA: " + str, level + SipStack.LOG_LEVEL_UA);
		if ((user_profile == null || !user_profile.no_prompt)
				&& level <= LogLevel.HIGH)
			System.out.println("UA: " + str);
	}

	void printException(Exception e, int level) {
		if (log != null)
			log.printException(e, level + SipStack.LOG_LEVEL_UA);
	}

}
