package org.lisasp.alphatimer.heats.api;

public enum LaneStatus {
    NotUsed, Used, Started, Finished, DidNotStart, DidNotFinish;

    public LaneStatus finish() {
        switch (this) {
            case Used:
                return LaneStatus.DidNotStart;
            case DidNotFinish:
                return  DidNotFinish;
            case NotUsed:
                return NotUsed;
            case DidNotStart:
                return DidNotStart;
            case Started:
                return LaneStatus.DidNotFinish;
            case Finished:
                return Finished;
        }
        throw new IllegalStateException("Fell through switch statement");
    }
}
