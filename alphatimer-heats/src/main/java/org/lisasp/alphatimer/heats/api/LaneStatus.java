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

    public LaneStatus timeUpdate() {
        switch (this) {
            case Used:
                return LaneStatus.Started;
            case DidNotFinish:
                return  DidNotFinish;
            case NotUsed:
                return NotUsed;
            case DidNotStart:
                return DidNotStart;
            case Started:
                return LaneStatus.Started;
            case Finished:
                return Finished;
        }
        throw new IllegalStateException("Fell through switch statement");
    }

    public boolean mayHaveTime() {
        switch (this) {
            case Used:
                return true;
            case DidNotFinish:
                return false;
            case NotUsed:
                return false;
            case DidNotStart:
                return false;
            case Started:
                return true;
            case Finished:
                return true;
        }
        throw new IllegalStateException("Fell through switch statement");
    }

    public LaneStatus used() {
        if (this == NotUsed) {
            return Used;
        }
        return this;
    }

    public LaneStatus unused() {
        return NotUsed;
    }
}
