import React ,{useState} from 'react';
import {CarInfo} from '@/components/gy';
import {Form, Modal,DatePicker} from 'antd';
import {getAccessToken} from "@/utils/token";
import moment from 'moment' ;
import {DeptInfo} from '@/components/gy' ;

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const ExportModal =(props) =>{

  const { exportModalVisible, onCancel  } = props;
  const [carNo,handleCarNo] = useState('') ;
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;
  const [tianbaoshijian,handleTianbaosj] = useState(moment(new Date()).format("YYYY-MM-DD") ) ;
  const [deptname,handleDeptname] = useState('' ) ;
  const [form] = Form.useForm();

  const getCarInfo = (e) =>{
    handleCarNo(e) ;
  }

  const handleExportBz = async () =>{

    fetch('/zxHealth/t/lqbz/file/exportJykxxtj', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({"bianzhidanwei":deptname,"numberplate":carNo,"tianbaoshijian":tianbaoshijian})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${deptname}加油卡信息统计表.xls`;// 设置文件名称
        aElement.href = blobUrl;// 设置a标签路径
        aElement.download = filename;
        aElement.click();
        window.URL.revokeObjectURL(blobUrl);
      });
    }).catch((error) => {
      console.log('文件下载失败', error);
    });
  }

  const okHandle = async () => {
    const fieldsValue = await form.validateFields();
    handleTianbaosj(fieldsValue.tianbaoshijian.format("YYYY-MM-DD")) ;
    // console.info(moment(fieldsValue.tianbaoshijian).format("YYYY-MM-DD")) ;
    handleExportBz();
    onCancel() ;
  }

  const changeDate =(e) =>{
    handleTianbaosj(moment(e).format("YYYY-MM-DD")) ;
  }

  const handleDept = (e) =>{
    // console.info(e)
    handleDeptname(e) ;
  }
  // eslint-disable-next-line no-unused-expressions
  return (<Modal
    destroyOnClose
    visible={exportModalVisible}
    onOk={okHandle}
    onCancel={onCancel}
  >
    <Form
      initialValues={{
        tianbaoshijian: moment(new Date())}}
      form={form}
    >
      <FormItem
        {...formItemLayout}
        label="单位"
        name="bianzhidanwei"
      >
        <DeptInfo onChange={handleDept}/>
      </FormItem>
      <FormItem
        {...formItemLayout}
        label="车牌号"
        name="numberplate"
      >
        <CarInfo deptname={deptname} onChange={getCarInfo}/>
      </FormItem>

      <FormItem
        {...formItemLayout}
        label="填报时间"
        name="tianbaoshijian"
        rules={[{ required: true, message: '请填写填报时间!' }]}

      >
        <DatePicker style={{width: '80%'}} onChange={changeDate}/>
      </FormItem>

    </Form>

  </Modal>
  )
}

export default ExportModal ;
