import React, {useEffect, useState} from 'react';
import {Select} from 'antd';
import {getCarInfo} from '@/services/gy'

const { Option } = Select;

const CarInfo = (props) =>{

  const {onChange,value,deptname}  = props ;
  const [CarInfoList,handleCarInfoList] = useState([]) ;
  const [defValue,handleDefValue] =useState() ;

  useEffect( () => {
    const lostData = async () => {
      let data ;
      if (deptname === undefined) {
        data =await getCarInfo({deptname:''}) ;
      }else{
        data = await getCarInfo({deptname});
      }
      handleCarInfoList(data.result)
    };

    lostData().then(() =>{
      handleDefValue(value || undefined)
    });
  }, [deptname]);

  const selectItem = (e) => {
    handleDefValue(e)
    onChange(e)
  }

  const options = CarInfoList.map(d => <Option key={d.numberplate}> {d.numberplate} </Option>);

  return (
    <Select onSelect={selectItem} value={defValue} style={{width: '80%'}}  >
      {options}
    </Select>
  )
}
export default CarInfo ;
