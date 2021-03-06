package org.strykeforce.thirdcoast.telemetry.sim;

import dagger.Component;
import javax.inject.Singleton;
import org.strykeforce.thirdcoast.telemetry.TelemetryController;
import org.strykeforce.thirdcoast.telemetry.Inventory;
import org.strykeforce.thirdcoast.telemetry.NetworkModule;

@Singleton
@Component(modules = {
    SimulatedInventoryModule.class,
    NetworkModule.class,
})
interface SimulationComponent {

  Inventory inventory();

  TelemetryController telemetryController();
}
