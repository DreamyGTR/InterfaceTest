package java.com.example.interfacetest;

import com.example.entity.InterfaceAndTestCaseBean;
import com.example.mapper.InterfaceAndTestCaseBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取数据list并转换成object类型数组
 */
public class TestDataProviderData {
    @Autowired
    InterfaceAndTestCaseBeanMapper data;

    @DataProvider(name = "test",parallel = true)
    public Iterator<Object[]> test(){
        List<InterfaceAndTestCaseBean> dataList = data.getInterfaceAndTestCaseBeanByInterfaceId(1);
        List<Object[]> oList=new ArrayList<>();
        for (InterfaceAndTestCaseBean i:dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
}
