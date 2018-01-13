package mag.near.lux.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
public class QuestWithList {

    public static QuestWithList of(MagePerson headHunter){
        return new QuestWithList (headHunter, new ArrayList<OffenderWithReward>());
    }

    private MagePerson headHunter;
    private List<OffenderWithReward> wantedList;

    public void addWanted(OffenderWithReward wanted){
        wantedList.add(wanted);
    }
}