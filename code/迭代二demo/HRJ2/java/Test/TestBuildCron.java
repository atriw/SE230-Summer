package Test;
import mod.BuildCron;

public class TestBuildCron {
    public static void main(String[] args) {
        int duration1 = new BuildCron().getDuration("12:00","14:00");
        int duration2 = new BuildCron().getDuration("02:00","04:00");
        int duration3 = new BuildCron().getDuration("02:00","10:00");
        int duration4 = new BuildCron().getDuration("01:59","02:00");
        int duration5 = new BuildCron().getDuration("02:30","03:30");

        System.out.println(duration1);
        System.out.println(duration2);
        System.out.println(duration3);
        System.out.println(duration4);
        System.out.println(duration5);

        String cron1 = new BuildCron().generate("02:00",1);
        String cron2 = new BuildCron().generate("14:00",2);
        String cron3 = new BuildCron().generate("06:30",3);
        System.out.println(cron1);
        System.out.println(cron2);
        System.out.println(cron3);
    }
}
