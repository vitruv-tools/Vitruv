package tools.vitruv.framework.util.command;

public abstract class VitruviusTransformationRecordingCommand extends VitruviusRecordingCommand {

    protected ChangePropagationResult transformationResult;

    public ChangePropagationResult getTransformationResult() {
        return this.transformationResult;
    }

}
