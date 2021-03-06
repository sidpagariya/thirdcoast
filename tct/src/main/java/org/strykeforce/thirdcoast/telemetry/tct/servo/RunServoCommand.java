package org.strykeforce.thirdcoast.telemetry.tct.servo;

import javax.inject.Inject;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
import org.strykeforce.thirdcoast.telemetry.tct.AbstractCommand;
import org.strykeforce.thirdcoast.telemetry.tct.Messages;

public class RunServoCommand extends AbstractCommand {

  public final static String NAME = "Run Selected Servo";
  private final ServoSet servoSet;

  @Inject
  public RunServoCommand(LineReader reader, ServoSet servoSet) {
    super(NAME, reader);
    this.servoSet = servoSet;
  }

  @Override
  public void perform() {
    if (servoSet.getServo() == null) {
      terminal.writer().println(Messages.boldRed("no servo selected"));
      return;
    }
    terminal.writer().println(Messages.bold("Enter servo setpoint, press <enter> to go back"));
    while (true) {
      String line;
      try {
        line = reader.readLine(Messages.prompt("setpoint or <return> to exit> ")).trim();
      } catch (EndOfFileException | UserInterruptException e) {
        continue;
      }

      if (line.isEmpty()) {
        return;
      }
      double setpoint;
      try {
        setpoint = Double.valueOf(line);
      } catch (NumberFormatException nfe) {
        terminal.writer().println(Messages.boldRed("please enter a number"));
        continue;
      }
      terminal.writer().print(Messages.bold(String.format("setting servo to %.2f%n", setpoint)));
      servoSet.getServo().set(setpoint);
    }

  }
}
