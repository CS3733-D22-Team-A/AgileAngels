package edu.wpi.agileAngels;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import java.io.IOException;

public class MenuSpeech implements Runnable {
  private static Configuration configuration = new Configuration();
  private static LiveSpeechRecognizer recognizer;
  private SpeechResult result;
  private static Thread t;
  private String resStr = "None";
  private boolean stop = false;

  public MenuSpeech() throws IOException {}

  public static void main(String[] args) throws Exception {

    // create an object of Runnable target
    MenuSpeech thread = new MenuSpeech();
    configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
    recognizer = new LiveSpeechRecognizer(configuration);

    // pass the runnable reference to Thread
    t = new Thread(thread, "CommandListener");

    // start the thread
    startConfiguration();
    t.start();

    // get the name of the thread
    System.out.println(t.getName());

    // configuration.setSampleRate(8000);

  }

  public static void startConfiguration() {
    recognizer.startRecognition(true);
  }

  public String checkString() {
    return resStr;
  }

  public String listen() throws IOException {
    result = recognizer.getResult();
    String out = "Hypothesis: " + result.getHypothesis();
    System.out.format("Hypothesis: %s\n", result.getHypothesis());
    out = findCommand(result);
    if (out.compareTo("lab") == 0) {
      stop = true;
      System.out.println("Command: Make Lab Request");
      closeRecognition();
      System.out.println("WE GOT LAB");
      resStr = "lab";
      t.interrupt();
    }

    return out;
    // Pause recognition process. It can be resumed then with startRecognition(false).
  }

  public String findCommand(SpeechResult out) {
    String res = "None";
    for (WordResult r : out.getWords()) {
      System.out.println("Word: " + r.getWord());
      if (r.getWord().toString().compareTo("lab") == 0
          || r.getWord().toString().compareTo("lap") == 0
          || r.getWord().toString().compareTo("ab") == 0
          || r.getWord().toString().compareTo("lad") == 0
          || r.getWord().toString().compareTo("latter") == 0) {
        System.out.println("IT's LAB");
        res = "lab";
        return res;
      }
    }

    return res;
  }

  public void closeRecognition() {
    recognizer.stopRecognition();
  }

  @Override
  public void run() {
    String res = null;
    try {
      res = listen();
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (res != null && (!stop)) {
      try {

        res = listen();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        wait(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
