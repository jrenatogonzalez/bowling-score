package com.rgonzalez.bowling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DefaultBowlerFrames implements BowlerFrames {
    private final String bowlerName;
    private final BowlFrameConstraints constraints;
    private final List<Frame> frames;

    public DefaultBowlerFrames(String bowlerName) {
        this.bowlerName = bowlerName;
        this.constraints = new BowlFrameConstraints();
        this.frames = new ArrayList<>();
    }

    public DefaultBowlerFrames(String bowlerName, BowlFrameConstraints constraints) {
        this.bowlerName = bowlerName;
        this.constraints = constraints;
        this.frames = new ArrayList<>();
    }

    @Override
    public String getBowlerName() {
        return this.bowlerName;
    }

    @Override
    public Optional<Integer> addRoll(int knockedDownPins) {
        if (!isFinished()) {
            Frame frameToAddRoll = chooseFrameToAddRoll();
            if (frameToAddRoll.isStrike() || frameToAddRoll.isSpare()) {
                return frameToAddRoll.addExtraRoll(knockedDownPins);
            } else {
                return frameToAddRoll.addRoll(knockedDownPins);
            }
        }
        return Optional.empty();
    }

    private Frame chooseFrameToAddRoll() {
        Frame frameToAddRoll;
        Optional<Frame> optionalCurrentFrame = getLastFrame();
        if (optionalCurrentFrame.isEmpty() || optionalCurrentFrame.get().isFinished()) {
            appendNewFrame();
            frameToAddRoll = getLastFrame().get();
        } else {
            frameToAddRoll = optionalCurrentFrame.get();
        }
        return frameToAddRoll;
    }

    private void appendNewFrame() {
        Frame frame = new DefaultFrame(
                constraints,
                new DefaultRollHandler(constraints.getMaxRolls(), constraints.getMaxPins()),
                new DefaultRollHandler(constraints.getMaxRolls(), constraints.getMaxPins(), true));
        frames.add(frame);
    }

    private Optional<Frame> getLastFrame() {
        return frames.isEmpty() ? Optional.empty() :
                Optional.of(frames.get(frames.size() - 1));
    }

    @Override
    public boolean isFinished() {
        return !frames.isEmpty() &&
                frames.size() == constraints.getMaxFrames() &&
                getLastFrame().get().isFinished();
    }

    @Override
    public Integer getScore() {
        return frames.stream()
                .map(Frame::getScore)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public Stream<Frame> getFrames() {
        return frames.stream();
    }
}
