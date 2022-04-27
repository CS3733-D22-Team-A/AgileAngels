package edu.wpi.agileAngels;

public class Main {

  public static void main(String[] args) throws Exception {
    // Men thread = new speechThread();
    // thread.run();

    MenuSpeech thread = new MenuSpeech();
    thread.main(args);

    // Aapp.launch(Aapp.class, args);
  }

  // test
}
