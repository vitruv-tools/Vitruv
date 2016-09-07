package eu.fpetersen.cbs.pc.display;

import eu.fpetersen.cbs.pc.data.Frame;
import eu.fpetersen.cbs.pc.graphics.IGraphicsCard;

public class Display implements IDisplay {

	private IGraphicsCard graphicsCard;

	public Display(IGraphicsCard graphicsCard) {
		this.graphicsCard = graphicsCard;
	}
	
	@Override
	public void drawFrame() {
		Frame frame = graphicsCard.renderFrame();
	}

}
