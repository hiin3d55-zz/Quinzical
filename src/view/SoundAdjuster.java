package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Button;

/**
 * This class represents the sound adjuster that allows the speed of the synthesis speech to be adjusted.
 * 
 * @author Dave Shin
 *
 */
public class SoundAdjuster {

	private double _speed;
	private String _text; 
	private boolean _clueFileCreated;
	private Button _fasterBtn;
	private Button _slowerBtn;
	private String _setToNZvoice;
	
	public SoundAdjuster(String text) {
		_speed = 1; // Default speed for synthesis speech is 1.
		_text = text;
		_clueFileCreated = false;
		_fasterBtn = new Button("Faster");
		_slowerBtn = new Button("Slower");
		_setToNZvoice = "(voice_akl_nz_jdt_diphone)\n";
	}
	
	public void createClueFile(String fileWriterArg) {
		try {
			File clueFile = new File("clueFile.scm");
			if (clueFile.createNewFile()) {
			} else {
				clueFile.delete();
				clueFile.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("Error with creating the file: clueFile.scm.");
			e.printStackTrace();
		}
		
		try {
			FileWriter clueFileWriter = new FileWriter("clueFile.scm");
			clueFileWriter.write(fileWriterArg);
			clueFileWriter.close();
		} catch (IOException e) {
			System.out.println("An error occured with writing to the file: clueFile.scm");
			e.printStackTrace();
		}
		
		_clueFileCreated = true;
	}
	
	public Button getFasterBtn() {
		return _fasterBtn;
	}
	
	public Button getSlowerBtn() {
		return _slowerBtn;
	}
	
	public String getSpeed() {
		return String.format("%.1f", _speed); // Round to 1dp.
	}
	
	public String getText() {
		return _text;
	}
	
	public void speak(String text) {	
		if (!_clueFileCreated) {
			createClueFile(_setToNZvoice + "(SayText \"" + text + "\")");
		}
	
		// Bash command for speaking out the clue.
		String speakClueCmd = "festival -b clueFile.scm";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
		try {
			Process process = builder.start();
			process.toString(); // This line does not do anything. It is just here so that the 
								// variable of process is used.
		}
		catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
	}
	
	public void fasterSpeed() {
		// Minimum value for _speed is 10. The extra 0.01 is there to prevent rounding errors.
		if (_speed > 0.21) {
			_speed -= 0.2;
		}
		
		createClueFile(_setToNZvoice + "(Parameter.set 'Duration_Stretch " + String.valueOf(_speed) 
						+ ")\n(SayText \"" + _text + "\")");
	}
	
	public void slowerSpeed() {
		// Maximum value for _speed is 10.
		if (_speed < 10) {
			_speed += 0.2;
		}
		
		createClueFile(_setToNZvoice + "(Parameter.set 'Duration_Stretch " + String.valueOf(_speed) 
						+ ")\n(SayText \"" + _text + "\")");
	}
}
