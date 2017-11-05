package org.strykeforce.thirdcoast.telemetry.tct.talon.config;

import com.ctre.CANTalon;
import java.util.Arrays;
import java.util.List;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
import org.strykeforce.thirdcoast.telemetry.tct.talon.TalonSet;

public abstract class AbstractFwdRevDoubleConfigCommand extends AbstractTalonConfigCommand {

  public AbstractFwdRevDoubleConfigCommand(String name, int weight, LineReader reader,
      TalonSet talonSet) {
    super(name, weight, reader, talonSet);
  }

  public AbstractFwdRevDoubleConfigCommand(String name, LineReader reader, TalonSet talonSet) {
    super(name, reader, talonSet);
  }

  protected abstract void config(CANTalon talon, double foward, double reverse);

  @Override
  public void perform() {
    double[] values = getFwdRevDoubles();
    if (values == null) {
      return;
    }
    for (CANTalon talon : talonSet.selected()) {
      config(talon, values[0], values[1]);
      logger.info("set {} for {} to {}/{}", name(), talon.getDescription(), values[0], values[1]);
    }
  }

  protected double[] getFwdRevDoubles() {
    terminal.writer().println("enter <forward>,<reverse> or a single number for both");
    double[] values = null;
    while (values == null) {
      String line = null;
      try {
        line = reader.readLine(prompt()).trim();
      } catch (EndOfFileException | UserInterruptException e) {
        break;
      }

      if (line.isEmpty()) {
        logger.info("{}: no value entered", name());
        break;
      }

      List<String> entries = Arrays.asList(line.split(","));
      double[] doubles = new double[2];
      try {
        if (entries.size() > 0) {
          doubles[0] = Double.valueOf(entries.get(0));
        } else {
          throw new AssertionError("entries was zero-length");
        }
        if (entries.size() > 1) {
          doubles[1] = Double.valueOf(entries.get(1));
        } else {
          doubles[1] = doubles[0];
        }
      } catch (NumberFormatException nfe) {
        terminal.writer().println("please enter a number or two numbers separated by a commma");
        continue;
      }
      values = doubles;
    }
    return values;
  }

}
