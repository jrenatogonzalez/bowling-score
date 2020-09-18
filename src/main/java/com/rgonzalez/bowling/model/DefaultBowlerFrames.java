package com.rgonzalez.bowling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
            int currentFrameIndex = getCurrentFrameIndex();
            Frame currentFrame = frames.get(currentFrameIndex);
            Optional<Integer> pinFalls;
            if (currentFrame.isRollsFinished() && isStrikeOrSpare(currentFrame)) {
                pinFalls = currentFrame.addExtraRoll(knockedDownPins);
            } else {
                pinFalls = currentFrame.addRoll(knockedDownPins);
            }
            addExtraRoll(this::isStrike, pinFalls, currentFrameIndex - 1);
            addExtraRoll(this::isStrikeOrSpare, pinFalls, currentFrameIndex);
            return pinFalls;
        }
        return Optional.empty();
    }

    private void addExtraRoll(
            Function<Frame, Boolean> frameCondition,
            Optional<Integer> knockedDownPins,
            int currentFrameIndex) {
        int previousFrameIndex = currentFrameIndex - 1;
        if (knockedDownPins.isPresent() && previousFrameIndex >= 0) {
            Frame previousFrame = frames.get(previousFrameIndex);
            if (frameCondition.apply(previousFrame)) {
                previousFrame.addExtraRoll(knockedDownPins.get());
            }
        }
        if (currentFrameIndex >= 0) {
            updatePreviousFrameScore(previousFrameIndex, frames.get(currentFrameIndex));
        }
    }

    private void updatePreviousFrameScore(int previousFrameIndex, Frame currentFrame) {
        if (previousFrameIndex >= 0) {
            Frame previousFrame = frames.get(previousFrameIndex);
            currentFrame.setPreviousFrameScore(previousFrame.getCumulativeScore());
        }
    }

    private Integer getCurrentFrameIndex() {
        if (frames.isEmpty()) {
            appendNewFrame();
        }
        Frame lastFrame = getLastFrame().get();
        if (lastFrame.isRollsFinished() && frames.size() < constraints.getMaxFrames()) {
            appendNewFrame();
        }
        return frames.size() - 1;
    }

    private boolean isStrikeOrSpare(Frame frame) {
        return frame.isSpare() || frame.isStrike();
    }

    private boolean isStrike(Frame frame) {
        return frame.isStrike();
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
