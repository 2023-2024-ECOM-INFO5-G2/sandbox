package polytech.ecom.g02.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import polytech.ecom.g02.web.rest.TestUtil;

class InfirmiereTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infirmiere.class);
        Infirmiere infirmiere1 = new Infirmiere();
        infirmiere1.setId(1L);
        Infirmiere infirmiere2 = new Infirmiere();
        infirmiere2.setId(infirmiere1.getId());
        assertThat(infirmiere1).isEqualTo(infirmiere2);
        infirmiere2.setId(2L);
        assertThat(infirmiere1).isNotEqualTo(infirmiere2);
        infirmiere1.setId(null);
        assertThat(infirmiere1).isNotEqualTo(infirmiere2);
    }
}
