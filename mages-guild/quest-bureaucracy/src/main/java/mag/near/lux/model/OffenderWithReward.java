package mag.near.lux.model;

import lombok.Value;

import java.util.Comparator;
import java.util.UUID;

@Value
public class OffenderWithReward {

    public OffenderWithReward(OffenderPerson offender){
        this.offender = offender;
        this.minReward = offender.getCrimes().stream()
                .mapToInt(crime -> crime.getType().getMin())
                .sum();
        this.maxReward = offender.getCrimes().stream()
                .mapToInt(crime -> crime.getType().getMax())
                .sum();
    }

    private OffenderPerson offender;
    private int minReward;
    private int maxReward;

}
