package org.strykeforce.thirdcoast.telemetry.tct.di;

import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Singleton;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

@Module
abstract class TerminalModule {

  @Provides
  @Singleton
  static Terminal provideTerminal() {
    try {
      return TerminalBuilder.terminal();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides
  @Singleton
  static LineReader provideLineReader(Terminal terminal) {
    return LineReaderBuilder.builder().terminal(terminal).appName("TCT").build();
  }
}
