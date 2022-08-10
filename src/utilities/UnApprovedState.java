package utilities;

public class UnApprovedState implements ReportState {

    @Override
    public ReportState setStatus() {
        return new ApprovedState();
    }

    @Override
    public boolean getState() {
        return false;
    }
}
