package com.hypnus.provider;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hypnus.Application;
import com.hypnus.provider.domain.City;
import com.hypnus.provider.domain.Country;
import com.hypnus.provider.mapper.CityMapper;
import com.hypnus.provider.mapper.CountryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hypnus on 2018/4/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class MapperTest {
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Test
    public void testFindOne() {
        City city = new City();
        city.setId(1L);
        City result = cityMapper.selectOne(city);
        Assert.assertEquals("石家庄", result.getName());
    }

    @Test
    public void testSelectPage() {
        PageHelper.startPage(0, 10);
        List<City> cities =  cityMapper.selectAll();
        Assert.assertEquals(new PageInfo<City>(cities).getTotal(), 2);
    }

    @Test
    public void testSelectPageAndOrder() {
        PageHelper.startPage(0, 1);
        PageHelper.orderBy("id DESC");
        List<City> cities =  cityMapper.selectAll();
        Assert.assertEquals(cities.get(0).getName(), "邯郸");
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    public void testUpdate() {
        City city = new City();
        city.setId(1L);
        city.setName("保定");
        city.setState("河北");
        cityMapper.updateByPrimaryKey(city);
    }

    @Transactional
    @Test
    @Rollback(false)
    public void testUpdateCounty() {
        Country country = new Country();
        country.setId(1);
        country.setCountryName("Angola2");
        country.setCountryCode("AO2");
        int rowCount = countryMapper.updateByPrimaryKey(country);
        Assert.assertEquals(rowCount, 1);
    }
}
