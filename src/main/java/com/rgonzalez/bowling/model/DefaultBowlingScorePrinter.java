package com.rgonzalez.bowling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DefaultBowlingScorePrinter implements BowlingScorePrinter {
    private static final String TAB = "\t";
    private static final String CR = "\n";
    private static final String STRIKE = "X";
    private static final String SPARE = "/";
    private static final String FRAMES = "Frames";
    private static final String PINFALLS = "Pinfalls";
    private static final String SCORE = "Score";

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
        StringBuilder sb = new StringBuilder(FRAMES).append(TAB.repeat(2));
        IntStream.rangeClosed(1, frames)
                .forEach(i -> sb.append(i).append(i == frames ? CR : TAB.repeat(2)));
        return sb.toString();
    }

    private String bowlerScore(BowlerFrames bowlerFrames) {
        StringBuilder sb = new StringBuilder(bowlerFrames.getBowlerName()).append(CR);
        sb.append(bowlerPinfalls(bowlerFrames.getFrames()));
        sb.append(bowlerFrameScores(bowlerFrames.getFrames()));
        return sb.toString();
    }

    private String bowlerPinfalls(Stream<Frame> frames) {
        List<Frame> framesList = frames.collect(Collectors.toList());
        StringBuilder sb = new StringBuilder(PINFALLS).append(TAB);
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
            sb.append(STRIKE).append(TAB);
            sb.append(
                    frame.getExtraRolls()
                            .map(chance -> chance.getPinFalls() == 10 ? STRIKE : chance.toString())
                            .collect(Collectors.joining(TAB))
            );
            sb.append(CR);
        } else {
            sb.append(TAB).append(STRIKE).append(TAB);
        }
        return sb.toString();
    }

    private String bowlerSpareFrame(Frame frame, boolean lastFrame) {
        StringBuilder sb = new StringBuilder();
        List<Chance> rolls = frame.getRolls()
                .collect(Collectors.toList());
        IntStream.range(0, rolls.size())
                .forEach(i -> sb.append(i == (rolls.size() - 1) ? SPARE + TAB : rolls.get(i) + TAB));
        if (lastFrame) {
            sb.append(frame.getExtraRolls()
                    .map(Chance::toString)
                    .findFirst().orElse(""))
                    .append(CR);
        }
        return sb.toString();
    }

    private String bowlerSimpleFrame(Frame frame, boolean lastFrame) {
        StringBuilder sb = new StringBuilder();
        sb.append(frame.getRolls()
                .map(Chance::toString)
                .collect(Collectors.joining(TAB))
        );
        if (lastFrame) {
            sb.append(CR);
        } else {
            sb.append(TAB);
        }
        return sb.toString();
    }

    private String bowlerFrameScores(Stream<Frame> frames) {
        List<Frame> framesList = frames.collect(Collectors.toList());
        StringBuilder sb = new StringBuilder(SCORE).append(TAB.repeat(2));
        IntStream.range(0, framesList.size())
                .forEach(i -> sb.append(framesList.get(i).getCumulativeScore())
                        .append(i == (framesList.size() - 1) ? CR : TAB.repeat(2)));
        return sb.toString();
    }

    private int maxFrames(List<BowlerFrames> bowlerFramesList) {
        return bowlerFramesList.stream()
                .map(bowlerFrames -> bowlerFrames.getFrames().count())
                .max(Long::compare).get().intValue();
    }

}
