package mag.near.lux.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import mag.near.lux.dto.CrimeTypeDTO;

@Value
@AllArgsConstructor
public class CrimeType {
    public CrimeType(CrimeTypeDTO crimeTypeDTO){
        this.typeName = crimeTypeDTO.getLevel();
        this.min = crimeTypeDTO.getMin();
        this.max = crimeTypeDTO.getMax();
    }

    private String typeName;
    private int min;
    private int max;
}
