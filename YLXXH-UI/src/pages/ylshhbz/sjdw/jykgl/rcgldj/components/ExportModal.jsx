import React ,{useState} from 'react';
import {CarInfo} from '@/components/gy';
import {Form, Modal} from 'antd';
import {getAccessToken} from "@/utils/token";

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const ExportModal =(props) =>{

  const { exportModalVisible, onCancel  } = props;
  const [carNo,handleCarNo] = useState('') ;
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;

  const getCarInfo = (e) =>{
    handleCarNo(e) ;
  }

  const handleExportBz = async () =>{
    fetch('/zxHealth/t/shh/file/exportJykrcgl', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({"numberplate":carNo})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${dept.name}油料社会化保障加油卡日常管理登记表.xls`;// 设置文件名称
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
    handleExportBz();
    onCancel() ;
  }


  // eslint-disable-next-line no-unused-expressions
  return (<Modal
    destroyOnClose
    visible={exportModalVisible}
    onOk={okHandle}
    onCancel={onCancel}
  >
    <Form

    >
      <FormItem
        {...formItemLayout}
        label="车牌号"
        name="numberplate"
      >
        <CarInfo onChange={getCarInfo}/>
      </FormItem>
    </Form>

  </Modal>
  )
}

export default ExportModal ;
