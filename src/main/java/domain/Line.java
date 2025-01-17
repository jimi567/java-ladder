package domain;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private final List<Bridge> bridges;

    public Line(Width width, BooleanGenerator booleanGenerator) {
        bridges = new ArrayList<>();
        for (int position = 0; position < width.get() - 1; position++) {
            addBridge(booleanGenerator);
        }
    }

    private void addBridge(BooleanGenerator booleanGenerator) {
        if (bridges.isEmpty() || checkPreviousBlank()) {
            bridges.add(Bridge.from(booleanGenerator));
            return;
        }
        bridges.add(Bridge.BLANK);
    }

    private boolean checkPreviousBlank() {
        return bridges.get(getBridgeCount() - 1) == Bridge.BLANK;
    }

    public int getBridgeCount() {
        return bridges.size();
    }

    public List<Boolean> getBridgesInformation() {
        return bridges.stream().map(Bridge::isExist).toList();
    }

    public int getNextPosition(int position) {
        if (isMovableLeft(position)) {
            return position - 1;
        }
        if (isMovableRight(position)) {
            return position + 1;
        }
        return position;
    }

    private boolean isMovableLeft(int position) {
        if(position == 0){
            return false;
        }
        return bridges.get(position - 1).isExist();
    }

    private boolean isMovableRight(int position) {
        if(position == bridges.size()){
            return false;
        }
        return bridges.get(position).isExist();
    }
}
