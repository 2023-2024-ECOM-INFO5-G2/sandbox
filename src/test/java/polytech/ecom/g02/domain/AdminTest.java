package polytech.ecom.g02.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import polytech.ecom.g02.web.rest.TestUtil;

class AdminTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Admin.class);
        Admin admin1 = new Admin();
        admin1.setId(1L);
        Admin admin2 = new Admin();
        admin2.setId(admin1.getId());
        assertThat(admin1).isEqualTo(admin2);
        admin2.setId(2L);
        assertThat(admin1).isNotEqualTo(admin2);
        admin1.setId(null);
        assertThat(admin1).isNotEqualTo(admin2);
    }
}
