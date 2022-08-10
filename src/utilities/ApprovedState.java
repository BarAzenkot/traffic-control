package utilities;

public class ApprovedState implements ReportState {

    @Override
    public ReportState setStatus() {
        return new UnApprovedState();
    }

    @Override
    public boolean getState() {
        return true;
    }
}
