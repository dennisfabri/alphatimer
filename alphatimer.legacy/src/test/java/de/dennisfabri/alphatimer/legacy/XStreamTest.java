package de.dennisfabri.alphatimer.legacy;

import com.thoughtworks.xstream.XStream;
import de.dennisfabri.alphatimer.legacy.model.LaneStatus;
import de.dennisfabri.alphatimer.legacy.model.Heat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XStreamTest {

    private static final String heatAsString = "Heat(lanes={0=0:, 1=1: 2:03,450 (RaceTimes), 2=2: 3:54,560 (ResultsOfThePreviousRace), 3=3: 5:45,670 (ResultsOfTheRace), 4=4: 7:36,000 (BackupOfThePreviousRace), 5=5: 9:27,800 (RunningTime)}, event=2, heat=3, id=1)";

    private Heat heat;
    private XStream xstream;

    @BeforeEach
    void prepare() {
        xstream = XStreamUtil.getXStream();

        heat = new Heat("1", 2, 3);
        heat.store(1, 123450, LaneStatus.RaceTimes);
        heat.store(2, 234560, LaneStatus.ResultsOfThePreviousRace);
        heat.store(3, 345670, LaneStatus.ResultsOfTheRace);
        heat.store(4, 456000, LaneStatus.BackupOfThePreviousRace);
        heat.store(5, 567800, LaneStatus.RunningTime);
        assertEquals(heatAsString, heat.toString());
    }

    @AfterEach
    void cleanup() {
        heat = null;
    }

    @Test
    void toXML() {
        String xml = xstream.toXML(heat);

        assertEquals(xmlRepresentation, xml);
    }

    @Test
    void fromXML() {
        Heat actual = (Heat) xstream.fromXML(xmlRepresentation);

        assertEquals(heat, actual);
    }

    private static final String xmlRepresentation =
            "<AlphaServer.Heat event=\"2\" heat=\"3\" id=\"1\">\n" +
                    "  <lanes>\n" +
                    "    <entry>\n" +
                    "      <int>0</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"0\">\n" +
                    "        <times/>\n" +
                    "        <stati/>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "      <int>1</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"1\">\n" +
                    "        <times>\n" +
                    "          <long>123450</long>\n" +
                    "        </times>\n" +
                    "        <stati>\n" +
                    "          <AlphaServer.LaneStatus>RaceTimes</AlphaServer.LaneStatus>\n" +
                    "        </stati>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "      <int>2</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"2\">\n" +
                    "        <times>\n" +
                    "          <long>234560</long>\n" +
                    "        </times>\n" +
                    "        <stati>\n" +
                    "          <AlphaServer.LaneStatus>ResultsOfThePreviousRace</AlphaServer.LaneStatus>\n" +
                    "        </stati>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "      <int>3</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"3\">\n" +
                    "        <times>\n" +
                    "          <long>345670</long>\n" +
                    "        </times>\n" +
                    "        <stati>\n" +
                    "          <AlphaServer.LaneStatus>ResultsOfTheRace</AlphaServer.LaneStatus>\n" +
                    "        </stati>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "      <int>4</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"4\">\n" +
                    "        <times>\n" +
                    "          <long>456000</long>\n" +
                    "        </times>\n" +
                    "        <stati>\n" +
                    "          <AlphaServer.LaneStatus>BackupOfThePreviousRace</AlphaServer.LaneStatus>\n" +
                    "        </stati>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "    <entry>\n" +
                    "      <int>5</int>\n" +
                    "      <AlphaServer.Lane laneindex=\"5\">\n" +
                    "        <times>\n" +
                    "          <long>567800</long>\n" +
                    "        </times>\n" +
                    "        <stati>\n" +
                    "          <AlphaServer.LaneStatus>RunningTime</AlphaServer.LaneStatus>\n" +
                    "        </stati>\n" +
                    "      </AlphaServer.Lane>\n" +
                    "    </entry>\n" +
                    "  </lanes>\n" +
                    "</AlphaServer.Heat>";
}
