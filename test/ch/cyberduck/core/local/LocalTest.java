package ch.cyberduck.core.local;

import ch.cyberduck.core.AbstractTestCase;
import ch.cyberduck.core.Local;
import ch.cyberduck.core.exception.AccessDeniedException;
import ch.cyberduck.core.exception.LocalAccessDeniedException;
import ch.cyberduck.core.preferences.PreferencesFactory;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @version $Id$
 */
public class LocalTest extends AbstractTestCase {

    @Test
    public void testList() throws Exception {
        assertFalse(new Local("profiles") {

        }.list().isEmpty());
    }

    private final class TestLocal extends Local {
        private TestLocal(final String name) {
            super(name);
        }
    }

    @Test(expected = AccessDeniedException.class)
    public void testReadNoFile() throws Exception {
        final String name = UUID.randomUUID().toString();
        TestLocal l = new TestLocal(System.getProperty("java.io.tmpdir") + "/" + name);
        l.getInputStream();
    }

    @Test
    public void testEqual() throws Exception {
        assertEquals(new TestLocal("/p/1"), new TestLocal("/p/1"));
        assertNotEquals(new TestLocal("/p/1"), new TestLocal("/p/2"));
        assertEquals(new TestLocal("/p/1"), new TestLocal("/P/1"));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(new TestLocal("/p/1").hashCode(), new TestLocal("/P/1").hashCode());
    }

    @Test
    public void testAttributes() throws Exception {
        final TestLocal l = new TestLocal("/p/1");
        assertNotNull(l.attributes());
        assertSame(l.attributes(), l.attributes());
    }

    @Test
    public void testDelimiter() {
        PreferencesFactory.get().setProperty("local.delimiter", "\\");
        try {
            Local l = new WindowsLocal("G:\\");
            assertEquals("G:\\", l.getAbsolute());
            assertEquals("", l.getName());

            l = new WindowsLocal("C:\\path\\relative");
            assertEquals("relative", l.getName());
            assertEquals("C:\\path\\relative", l.getAbsolute());

            l = new WindowsLocal("C:\\path", "cyberduck.log");
            assertEquals("cyberduck.log", l.getName());
            assertEquals("C:\\path\\cyberduck.log", l.getAbsolute());

            l = new WindowsLocal("C:\\path", "Sessions");
            assertEquals("Sessions", l.getName());
            assertEquals("C:\\path\\Sessions", l.getAbsolute());
        }
        finally {
            PreferencesFactory.get().deleteProperty("local.delimiter");
        }
    }

    private static class WindowsLocal extends Local {

        public WindowsLocal(final String parent, final String name) {
            super(parent, name);
        }

        public WindowsLocal(final Local parent, final String name) {
            super(parent, name);
        }

        public WindowsLocal(final String name) {
            super(name);
        }

        @Override
        public char getDelimiter() {
            return '\\';
        }
    }

    @Test(expected = LocalAccessDeniedException.class)
    public void testRenameExistingDirectory() throws Exception {
        final TestLocal l = new TestLocal(System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString());
        l.mkdir();
        final TestLocal n = new TestLocal(System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString());
        n.rename(l);
    }

    @Test
    public void testRenameDirectory() throws Exception {
        final TestLocal l = new TestLocal(System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString());
        final TestLocal n = new TestLocal(System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString());
        n.rename(l);
        assertTrue(l.exists());
        l.delete();
    }
}
