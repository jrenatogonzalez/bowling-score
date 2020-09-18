package com.rgonzalez.bowling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DefaultBowlingScorePrinter implements BowlingScorePrinter {

    @Override
    public Stream<String> printScore(Stream<BowlerFrames> bowlerFramesStream) {
        List<String> scoreList = new ArrayList<>();
        List<BowlerFrames> bowlerFramesList = bowlerFramesStream.collect(Collectors.toList());
        if (!bowlerFramesList.isEmpty()) {
            int frames = maxFrames(bowlerFramesList);
            scoreList.add(frameHeader(frames));
            bowlerFramesList.forEach(bowlerFrame -> scoreList.add(bowlerScore(bowlerFrame)));
        }
        return scoreList.stream();
    }

    private String frameHeader(int frames) {
        StringBuilder sb = new StringBuilder("Frames\t\t");
        IntStream.rangeClosed(1, frames)
                .forEach(i -> sb.append(i).append(i == frames ? "\n" : "\t\t"));
        return sb.toString();
    }

    private String bowlerScore(BowlerFrames bowlerFrames) {
        StringBuilder sb = new StringBuilder(bowlerFrames.getBowlerName()).append("\n");
        sb.append(bowlerPinfalls(bowlerFrames.getFrames()));
        sb.append(bowlerFrameScores(bowlerFrames.getFrames()));
        return sb.toString();
    }

    private String bowlerPinfalls(Stream<Frame> frames) {
        List<Frame> framesList = frames.collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("Pinfalls\t");
        IntStream.range(0, framesList.size())
                .forEach(i -> sb.append(bowlerFrame(framesList.get(i), i == (framesList.size() - 1))));
        return sb.toString();
    }

    private String bowlerFrame(Frame frame, boolean lastFrame) {
        if (frame.isStrike()) {
            return bowlerStrikeFrame(frame, lastFrame);
        } else if (frame.isSpare()) {
            return bowlerSpareFrame(frame, lastFrame);
        } else {
            return bowlerSimpleFrame(frame, lastFrame);
        }
    }

    private String bowlerStrikeFrame(Frame frame, boolean lastFrame) {
        StringBuilder sb = new StringBuilder();
        if (lastFrame) {
            sb.append("X").append("\t");
            frame.getExtraRolls().forEach(roll -> sb.append(roll.equals(10) ? "X\t" : String.valueOf(roll) + "\t"));
            sb.append("\n");
        } else {
            sb.append("\t").append("X").append("\t");
        }
        return sb.toString();
    }

    private String bowlerSpareFrame(Frame frame, boolean lastFrame) {
        StringBuilder sb = new StringBuilder();
        List<Integer> rolls = frame.getRolls().collect(Collectors.toList());
        IntStream.range(0, rolls.size())
                .forEach(i -> sb.append(i == (rolls.size() - 1) ? "/\t" : rolls.get(i) + "\t"));
        if (lastFrame) {
            sb.append(frame.getExtraRolls()
                    .map(String::valueOf)
                    .findFirst().orElse(""))
                    .append("\n");
        }
        return sb.toString();
    }

    private String bowlerSimpleFrame(Frame frame, boolean lastFrame) {
        StringBuilder sb = new StringBuilder();
        frame.getRolls().forEach(roll -> sb.append(roll).append("\t"));
        if (lastFrame) {
            sb.append("\n");
        }
        return sb.toString();
    }

    private String bowlerFrameScores(Stream<Frame> frames) {
        List<Frame> framesList = frames.collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("Score\t\t");
        IntStream.range(0, framesList.size())
                .forEach(i -> sb.append(framesList.get(i).getCumulativeScore())
                        .append(i == (framesList.size() - 1) ? "\n" : "\t\t"));
        return sb.toString();
    }

    private int maxFrames(List<BowlerFrames> bowlerFramesList) {
        return bowlerFramesList.stream()
                .map(bowlerFrames -> bowlerFrames.getFrames().count())
                .max(Long::compare).get().intValue();
    }

}
