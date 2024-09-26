package sn.ashia.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class UserSeeder implements CommandLineRunner {
    private final UserService userService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void run(String... args) throws Exception {
        if (profile.equals("dev") || profile.equals("pre-prod")) {
            User traxyax = new User(
                    "traxyax@gmail.com",
                    Set.of(UserRole.ADMIN)
            );

            User odadjerk = new User(
                    "odadjerk@gmail.com",
                    Set.of(UserRole.ADMIN)
            );

            User stracsys = new User(
                    "stracsys@gmail.com",
                    Set.of(UserRole.PROJECT_MANAGER)
            );

            for (User user : List.of(
                    traxyax, odadjerk, stracsys
            )) {
                userService.saveIfEmpty(user);
            }

            return;
        }

        User aboubacar = new User(
                "aboubacar.traore@ashia.sn",
                Set.of(UserRole.ADMIN)
        );

        User serge = new User(
                "serge.odadje@ashia.sn",
                Set.of(UserRole.ADMIN)
        );

        User fanta = new User(
                "fanta.hemeryck@ashia.sn",
                Set.of(UserRole.PROJECT_DIRECTOR)
        );

        User danielle = new User(
                "danielle.fonceka@ashia.sn",
                Set.of(UserRole.PROJECT_MANAGER)
        );

        User penda = new User(
                "penda.diakhate@ashia.sn",
                Set.of(UserRole.PROJECT_MANAGER)
        );

        User sedera = new User(
                "sedera.rakotonirainy@ashia.sn",
                Set.of(UserRole.PROJECT_MANAGER_PLUS)
        );

        User jeyssica = new User(
                "jeyssica.sodjinou@ashia.sn",
                Set.of(UserRole.PROJECT_MANAGER)
        );

        for (User user : List.of(
                aboubacar, serge, fanta, danielle, penda, sedera, jeyssica
        )) {
            userService.saveIfEmpty(user);
        }
    }
}
