package eass.mas.nxt;

import lejos.nxt.LightSensor;

public class RoverLightSensor extends LightSensor implements LegoSensor {

	public RoverLightSensor(NXTBrick brick, int i) {
		super(brick.getSensorPort(i));
	}

}
