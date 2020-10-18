package speech;

import java.io.IOException;

public class SpeechSynthesisThread extends Thread {
	
	@Override
	public void run() {
		// Bash command for speaking out the clue.
		String speakClueCmd = "festival -b clueFile.scm";
				
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
		try {
			Process process = builder.start();
			try {
				process.waitFor(); 
			} catch (InterruptedException ie) {
				System.out.println("Error with waiting for the speech synthesis process to finish.");
				ie.printStackTrace();	
			}
		} catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
	}
}
