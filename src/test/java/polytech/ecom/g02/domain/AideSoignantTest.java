package polytech.ecom.g02.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import polytech.ecom.g02.web.rest.TestUtil;

class AideSoignantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AideSoignant.class);
        AideSoignant aideSoignant1 = new AideSoignant();
        aideSoignant1.setId(1L);
        AideSoignant aideSoignant2 = new AideSoignant();
        aideSoignant2.setId(aideSoignant1.getId());
        assertThat(aideSoignant1).isEqualTo(aideSoignant2);
        aideSoignant2.setId(2L);
        assertThat(aideSoignant1).isNotEqualTo(aideSoignant2);
        aideSoignant1.setId(null);
        assertThat(aideSoignant1).isNotEqualTo(aideSoignant2);
    }
}
