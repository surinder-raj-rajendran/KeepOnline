import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Moves the mouse pointer by a tiny random amount at random intervals so
 * status-based apps like Microsoft Teams keep showing you as "Active"
 * instead of flipping to "Away"/"Idle".
 *
 * Usage:
 *   java KeepOnline
 *   java KeepOnline --min-interval 20 --max-interval 90 --distance 15
 *
 * Press Ctrl+C to stop.
 */
public class KeepOnline {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void log(String message) {
        System.out.println("[" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + "] " + message);
    }

    private static void jiggleMouse(Robot robot, Random random, int distance) throws InterruptedException {
        Point origin = MouseInfo.getPointerInfo().getLocation();
        int dx = random.nextInt(2 * distance + 1) - distance;
        int dy = random.nextInt(2 * distance + 1) - distance;

        robot.mouseMove(origin.x + dx, origin.y + dy);
        Thread.sleep(100 + random.nextInt(200));
        robot.mouseMove(origin.x, origin.y);
    }

    public static void main(String[] args) {
        double minInterval = 30;
        double maxInterval = 120;
        int distance = 10;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--min-interval":
                    minInterval = Double.parseDouble(args[++i]);
                    break;
                case "--max-interval":
                    maxInterval = Double.parseDouble(args[++i]);
                    break;
                case "--distance":
                    distance = Integer.parseInt(args[++i]);
                    break;
                default:
                    System.err.println("Unknown argument: " + args[i]);
                    System.exit(1);
            }
        }

        if (minInterval <= 0 || maxInterval < minInterval) {
            System.err.println("Invalid interval range: require 0 < min-interval <= max-interval");
            System.exit(1);
        }

        Random random = new Random();
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Unable to control the mouse on this system: " + e.getMessage());
            System.exit(1);
            return;
        }

        log("Starting mouse-jiggler to keep Microsoft Teams active. Press Ctrl+C to stop.");
        log(String.format("Interval: %.0f-%.0fs, distance: %dpx", minInterval, maxInterval, distance));

        try {
            while (true) {
                double waitSeconds = minInterval + random.nextDouble() * (maxInterval - minInterval);
                Thread.sleep((long) (waitSeconds * 1000));
                jiggleMouse(robot, random, distance);
                log(String.format("Jiggled mouse, next move in ~%.0fs", waitSeconds));
            }
        } catch (InterruptedException e) {
            log("Stopped.");
        }
    }
}
