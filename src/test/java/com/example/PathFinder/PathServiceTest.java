package com.example.PathFinder;


import com.example.PathFinder.domain.Path;
import com.example.PathFinder.service.PathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PathServiceTest {
    @Autowired
    private PathService pathService;

    @Test
    public void testShortestPathInMonaco() {
        //coordinates of Tunnel Larvotto
        double latStart = 43.739749;
        double longStart = 7.429885;
        //coordinates of Villa Sauber
        double latFinish = 43.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = pathService.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(path);

    }

    @Test
    public void testShortestPathInMonaco2() {
        //coordinates of Place du Canton
        double latStart = 43.731579;
        double longStart = 7.417277;
        //coordinates of Villa Sauber
        double latFinish = 43.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = pathService.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(path);

    }

    @Test
    public void testShortestPathOutsideMonaco() {
        //coordinates of Tunnel Larvotto
        double latStart = 43.739749;
        double longStart = 7.429885;
        //coordinates sth in the North
        double latFinish = 63.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = pathService.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNull(path);

    }
}
