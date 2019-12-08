package checker;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import data.Employee;
import data.Entry;
import data.Profession;
import data.TimeSheet;
import data.TimeSpan;
import data.WorkingArea;

public class MiLoGCheckerTimeOverlapTest {

    ////Placeholder for documentation construction
    private static final Employee EMPLOYEE = new Employee("Max Mustermann", 1234567);
    private static final Profession PROFESSION = new Profession("Fakultät für Informatik", WorkingArea.UB, new TimeSpan(40, 0), 10.31);
    private static final YearMonth YEAR_MONTH = YearMonth.of(2019, Month.NOVEMBER);
    private static final TimeSpan zeroTs = new TimeSpan(0, 0);
    
    @Test
    public void testEmptyEntries() {
        ////Test values
        Entry[] entries = {};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.VALID, checker.getResult());
        assertTrue(checker.getErrors().isEmpty());
    }
    
    @Test
    public void testSingleEntry() {
        ////Test values
        TimeSpan start = new TimeSpan(8, 0);
        TimeSpan end = new TimeSpan(12, 0);
        TimeSpan pause = zeroTs;
        LocalDate date = LocalDate.of(2019, 11, 22);
        
        Entry entry = new Entry("Test", date, start, end, pause);
        Entry[] entries = {entry};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.VALID, checker.getResult());
        assertTrue(checker.getErrors().isEmpty());
    }
    
    @Test
    public void testMultipleEntriesWithoutOverlap() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(14, 0);
        TimeSpan end1 = new TimeSpan(18, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry0, entry1, entry2};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.VALID, checker.getResult());
        assertTrue(checker.getErrors().isEmpty());
    }
    
    @Test
    public void testMultipleEntriesWithoutOverlapUnsorted() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(14, 0);
        TimeSpan end1 = new TimeSpan(18, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry1, entry2, entry0};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.VALID, checker.getResult());
        assertTrue(checker.getErrors().isEmpty());
    }
    
    @Test
    public void testMultipleEntriesWithoutOverlapTouching() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(12, 0);
        TimeSpan end1 = new TimeSpan(18, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry0, entry1, entry2};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.VALID, checker.getResult());
        assertTrue(checker.getErrors().isEmpty());
    }
    
    @Test
    public void testMultipleEntriesWithOverlap() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(11, 0);
        TimeSpan end1 = new TimeSpan(13, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry0, entry1, entry2};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.INVALID, checker.getResult());
        assertTrue(checker.getErrors().stream().anyMatch(item -> item.getErrorMessage().equals(MiLoGChecker.CheckerErrorMessage.TIME_OVERLAP.getErrorMessage())));
    }
    
    @Test
    public void testMultipleEntriesWithOverlapUnsorted() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(11, 0);
        TimeSpan end1 = new TimeSpan(13, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry1, entry2, entry0};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.INVALID, checker.getResult());
        assertTrue(checker.getErrors().stream().anyMatch(item -> item.getErrorMessage().equals(MiLoGChecker.CheckerErrorMessage.TIME_OVERLAP.getErrorMessage())));
    }
    
    @Test
    public void testMultipleEntriesWithOverlapMinutes() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(12, 30);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(12, 15);
        TimeSpan end1 = new TimeSpan(14, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry0, entry1, entry2};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.INVALID, checker.getResult());
        assertTrue(checker.getErrors().stream().anyMatch(item -> item.getErrorMessage().equals(MiLoGChecker.CheckerErrorMessage.TIME_OVERLAP.getErrorMessage())));
    }
    
    @Test
    public void testMultipleEntriesWithOneIntervalInAnother() {
        ////Test values
        TimeSpan start0 = new TimeSpan(8, 0);
        TimeSpan end0 = new TimeSpan(14, 0);
        TimeSpan pause0 = zeroTs;
        LocalDate date0 = LocalDate.of(2019, 11, 22);
        Entry entry0 = new Entry("Test 0", date0, start0, end0, pause0);
        
        TimeSpan start1 = new TimeSpan(11, 0);
        TimeSpan end1 = new TimeSpan(13, 0);
        TimeSpan pause1 = zeroTs;
        LocalDate date1 = LocalDate.of(2019, 11, 22);
        Entry entry1 = new Entry("Test 1", date1, start1, end1, pause1);
        
        TimeSpan start2 = new TimeSpan(8, 0);
        TimeSpan end2 = new TimeSpan(12, 0);
        TimeSpan pause2 = zeroTs;
        LocalDate date2 = LocalDate.of(2019, 11, 25);
        Entry entry2 = new Entry("Test 2", date2, start2, end2, pause2);
        
        Entry[] entries = {entry0, entry1, entry2};
        TimeSheet fullDoc = new TimeSheet(EMPLOYEE, PROFESSION, YEAR_MONTH, entries, zeroTs, zeroTs, zeroTs);
        
        ////Checker initialization
        MiLoGChecker checker = new MiLoGChecker(fullDoc);
        
        ////Execution
        checker.checkTimeOverlap();
        
        ////Assertions
        assertEquals(CheckerReturn.INVALID, checker.getResult());
        assertTrue(checker.getErrors().stream().anyMatch(item -> item.getErrorMessage().equals(MiLoGChecker.CheckerErrorMessage.TIME_OVERLAP.getErrorMessage())));
    }
    
}
