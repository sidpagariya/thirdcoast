package org.strykeforce.thirdcoast.telemetry.tct.talon.config.cl

import org.strykeforce.thirdcoast.telemetry.tct.Command
import org.strykeforce.thirdcoast.telemetry.tct.talon.config.AbstractTalonConfigCommandTest

class IZoneCommandTest extends AbstractTalonConfigCommandTest {

    Command command

    @Override
    void setup() {
        command = new IZoneCommand(reader, talonSet)
    }


    def "nothing input"() {
        when:
        command.perform()

        then:
        1 * reader.readLine(_) >> ""
        0 * talon._
    }

    def "handles input"() {
        when:
        command.perform()

        then:
        2 * reader.readLine(_) >>> ["ABC", "2767"]

        1 * writer.println("please enter an integer") // ABC

        1 * talon.setIZone(2767) // 2767
        1 * talon.getDescription()
        0 * talon._
    }
}
