package ru.vtb.carrent.car.util;

import org.mockito.Mock;
import org.testng.annotations.Test;

import javax.persistence.criteria.CriteriaBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class RepositoryHelperTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    public RepositoryHelperTest() {
        initMocks(this);
    }

    @Test
    public void testGetEqualCriteriaLong() {
        RepositoryHelper.getEqualCriteria("1", Long.class, criteriaBuilder, null, false);
        verify(criteriaBuilder).equal(null, Long.valueOf("1"));
    }

    @Test
    public void testGetEqualCriteriaInt() {
        RepositoryHelper.getEqualCriteria("1", Integer.class, criteriaBuilder, null, false);
        verify(criteriaBuilder).equal(null, Integer.valueOf("1"));
    }

    @Test
    public void testGetEqualCriteriaDouble() {
        RepositoryHelper.getEqualCriteria("1", Double.class, criteriaBuilder, null, false);
        verify(criteriaBuilder).equal(null, Double.valueOf("1"));
    }

    @Test
    public void testGetEqualCriteriaBolean() {
        RepositoryHelper.getEqualCriteria("true", Boolean.class, criteriaBuilder, null, false);
        verify(criteriaBuilder).equal(null, Boolean.valueOf("true"));
    }

    @Test
    public void testGetEqualCriteriaLongConjunction() {
        RepositoryHelper.getEqualCriteria("x", Long.class, criteriaBuilder, null, false);
        verify(criteriaBuilder, atLeast(1)).conjunction();
    }

    @Test
    public void testGetEqualCriteriaIntConjunction() {
        RepositoryHelper.getEqualCriteria("x", Integer.class, criteriaBuilder, null, false);
        verify(criteriaBuilder, atLeast(1)).conjunction();
    }

    @Test
    public void testGetEqualCriteriaDoubleConjunction() {
        RepositoryHelper.getEqualCriteria("x", Double.class, criteriaBuilder, null, false);
        verify(criteriaBuilder, atLeast(1)).conjunction();
    }

    @Test
    public void testGetBetweenCriteriaLong() {
        List<String> testData = Arrays.asList("1", "2");
        RepositoryHelper.getBetweenCriteria(testData, Long.class, criteriaBuilder, null);
        verify(criteriaBuilder).between(null, Long.valueOf(testData.get(0)), Long.valueOf(testData.get(1)));
    }

    @Test
    public void testGetBetweenCriteriaInt() {
        List<String> testData = Arrays.asList("1", "2");
        RepositoryHelper.getBetweenCriteria(testData, Integer.class, criteriaBuilder, null);
        verify(criteriaBuilder).between(null, Integer.valueOf(testData.get(0)), Integer.valueOf(testData.get(1)));
    }

    @Test
    public void testGetBetweenCriteriaDouble() {
        List<String> testData = Arrays.asList("1", "2");
        RepositoryHelper.getBetweenCriteria(testData, Double.class, criteriaBuilder, null);
        verify(criteriaBuilder).between(null, Double.valueOf(testData.get(0)), Double.valueOf(testData.get(1)));
    }

    @Test
    public void testGetBetweenCriteriaDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        List<String> testData = Arrays.asList("2018-06-11", "2018-06-18");
        RepositoryHelper.getBetweenCriteria(testData, Date.class, criteriaBuilder, null);
        verify(criteriaBuilder).between(null, sdf.parse(testData.get(0)), sdf.parse(testData.get(1)));
    }

    @Test
    public void testGetBetweenCriteriaLongConjunction() {
        List<String> testData = Arrays.asList("1", "x");
        RepositoryHelper.getBetweenCriteria(testData, Long.class, criteriaBuilder, null);
        verify(criteriaBuilder, atLeast(1)).conjunction();    }

    @Test
    public void testGetBetweenCriteriaIntConjunction() {
        List<String> testData = Arrays.asList("1", "x");
        RepositoryHelper.getBetweenCriteria(testData, Integer.class, criteriaBuilder, null);
        verify(criteriaBuilder, atLeast(1)).conjunction();    }

    @Test
    public void testGetBetweenCriteriaDoubleConjunction() {
        List<String> testData = Arrays.asList("1", "x");
        RepositoryHelper.getBetweenCriteria(testData, Double.class, criteriaBuilder, null);
        verify(criteriaBuilder, atLeast(1)).conjunction();    }

    @Test
    public void testGetBetweenCriteriaDateConjunction() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        List<String> testData = Arrays.asList("2018-06-11", "x");
        RepositoryHelper.getBetweenCriteria(testData, Date.class, criteriaBuilder, null);
        verify(criteriaBuilder, atLeast(1)).conjunction();    }
}