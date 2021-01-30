package com.gladurbad.anticheattestutil.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class SpeedTest {
    private final Location start, end, reset;
    private final Player player;
    private final long startTime;
    private boolean allowedToMove;

    public SpeedTest(final Player player, final Location start, final Location end, final Location reset) {
        this.player = player;
        this.start = start;
        this.reset = reset;
        this.end = end;
        this.player.teleport(
                new Location(
                        player.getWorld(),
                        start.getX(),
                        start.getY(),
                        start.getZ(),
                        getOptimalRotation()[0],
                        getOptimalRotation()[1]
                )
        );
        this.startTime = System.currentTimeMillis();
        this.allowedToMove = false;
    }

    public boolean finishedTest() {
        return player.getLocation().toVector().setY(0).distance(end.toVector().setY(0)) < 2.5;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public double getSpeedInSeconds() {
        final double distance = start.distance(end) - 2.5;
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        return distance/seconds;
    }

    public double getSpeedInTicks() {
        final double distance = start.distance(end) - 2.5;
        final long seconds = getElapsedTime() / 50L;
        return distance/seconds;
    }

    public Pair<Double, Double> getSpeedPercentage() {
        return new Pair<Double, Double>(
                (getSpeedInSeconds() / 4.85) * 100,
                (getSpeedInTicks() / 0.25) * 100
        );
    }

    private float[] getOptimalRotation() {
        final Location origin = this.start.clone();
        final Location target = this.end.clone();

        return new float[]{
                origin.setDirection(target.subtract(origin.toVector()).toVector()).getYaw(),
                0F
        };
    }

    public void endTest() {
        player.teleport(reset);
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public Player getPlayer() {
        return player;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isAllowedToMove() {
        return allowedToMove;
    }

    public void setAllowedToMove(final boolean allowedToMove) {
        this.allowedToMove = allowedToMove;
    }
}
