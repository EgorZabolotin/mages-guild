package mag.near.lux.model;

import lombok.Getter;

public enum Rank {
    APPRENTICE(40),
    INTERN(60),
    WIZARD(80),
    AUROR(100),
    UNDEFIND(Integer.MAX_VALUE);

    Rank(int i) {
        this.value = i;
    }

    @Getter
    private int value;
}
