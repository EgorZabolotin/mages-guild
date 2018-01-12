package mag.near.lux.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;

@Value
@AllArgsConstructor
public class Quest {

public static Quest of(MagePerson headHunter, OffenderWithReward wanted){
    return new Quest(headHunter,wanted);
}

    private MagePerson headHunter;
    private OffenderWithReward wanted;
}
