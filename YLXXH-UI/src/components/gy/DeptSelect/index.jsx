import React, {useEffect, useState} from 'react';
import {Select} from 'antd';
import {getSysDeptList} from '@/services/gy'

const { Option } = Select;

const DeptInfo = (props) =>{

  const {onChange,value}  = props ;
  const [deptInfoList,handleDeptInfoList] = useState([]) ;
  const [defValue,handleDefValue] =useState() ;

  useEffect( () => {
    const lostData = async () => {
      const data = await getSysDeptList();
      handleDeptInfoList(data.result)
    };

    lostData().then(() =>{
      handleDefValue(value || undefined)
    });
  }, []);

  const selectItem = (e) => {
    handleDefValue(e)
    onChange(e)
  }

  const options = deptInfoList.map(d => <Option key={d.name}> {d.name} </Option>);

  return (
    <Select onSelect={selectItem} value={defValue} style={{width: '80%'}}>
      {options}
    </Select>
  )
}
export default DeptInfo ;
