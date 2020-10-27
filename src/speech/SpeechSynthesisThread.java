package speech;

import java.io.IOException;

public class SpeechSynthesisThread extends Thread {
	
	private static SpeechSynthesisThread _thread;
	private Process _process;
	
	/**
     * This class is a singleton class. Instances of it cannot be made in other places.
     */
    private SpeechSynthesisThread() {}
    

    /**
     * Use this static method if the instance of SoundAdjuster is needed. This ensures that only
     * one instance of SoundAdjuster is created.
     */
    public static SpeechSynthesisThread getInstance() {
        if (_thread == null) {
        	_thread = new SpeechSynthesisThread();
        }
        return _thread;
    }
	
	@Override
	public void run() {
		// Bash command for speaking out the clue.
		String speakClueCmd = "festival -b clueFile.scm";
				
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
		try {
			if (_process != null) {
				_process.descendants().forEach(ProcessHandle::destroy);
				_process.destroyForcibly();
				_process.destroy();
			}
			Process _process = builder.start();
			try {
				_process.waitFor(); 
			} catch (InterruptedException ie) {
				System.out.println("Error with waiting for the speech synthesis process to finish.");
				ie.printStackTrace();	
			}
		} catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
	}
	
	public void stopSpeech() {
		System.out.println(_process);
		_process.descendants().forEach(ProcessHandle::destroy);
		_process.destroy();
	}
}
