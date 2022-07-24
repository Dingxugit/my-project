import React,{useRef,useEffect,useState} from 'react' ;
import {Modal,Button} from 'antd' ;
import Print from 'react-print-html'
import {getJypzById,saveTozbz} from '@/services/ylshh/jygl'
import { ExclamationCircleOutlined } from '@ant-design/icons';

const PrintModal = (props) =>{

  const {printModalVisible,onCancel,okHandle,jyId} = props;
  const [jypz,handleJypz] =useState({}) ;
  const [confirmMess,handleConfirmMess] =useState('') ;

  const printRef =useRef() ;

  useEffect(() =>{
    const lostData = async ()=>{
      const response = await getJypzById({id:jyId})
      return response ;
    }
    lostData().then((res) =>{
      if (res.result){
        handleJypz(res.result);
        if (res.result.isbr > 0){
          handleConfirmMess("日常管理中本次加油金额大于结存数，是否继续打印，打印后，油料经费指标中的结存将为负数");
        }else if(res.result.isbr === 0){
          handleConfirmMess("未录入登记信息，金额无法计入到指标账，是否继续打印");
        }
      }
    })
  }, [jyId])

  function handleProps() {
    // document.querySelector('#print').style.display="block"
    Print(printRef.current)
    onCancel()
    saveTozbz({id:jyId}) ;
  }
  function confirm() {
    Modal.confirm({
      title: '是否打印？',
      icon: <ExclamationCircleOutlined />,
      content: `${confirmMess}`,
      okText: '确认',
      cancelText: '取消',
      onOk: ()=>handleProps(),
      onCancel: ()=>onCancel()
    });
  }


  return(
        <Modal
          destroyOnClose
          visible={printModalVisible}
          width={800}
          bodyStyle={{height: 500}}
          closable={false}
          okButtonProps={{ hidden: true }}
          onCancel={onCancel}
        >

          <Button onClick={confirm} type="primary" style={{ marginBottom: 20 }}>
            打印
          </Button>

          <div id="print" ref={printRef} style={{textAlign: 'center',width: '100%'}} >
            <div style={{fontSize: '25px',fontWeight: 'bolder' ,width: '100%'}}><h1>油料社会化保障加油凭证</h1></div>
            <div style={{width: '100%'}}>
            <table  cellspacing= '0' border='1px' width="100%" >
              <tbody >
                <tr >
                  <td width='12.5%' >用油单位</td>
                  <td width='50%' colSpan="4">{jypz.yongyoudanwei}</td>
                  <td width='12.5%'>充值时间</td>
                  <td width='25%' colSpan="2">{jypz.chongzhisj}</td>
                </tr>
                <tr >
                  <td width='12.5%' rowSpan={4}>加油明细</td>
                  <td width='12.5%' rowSpan={2}>车牌号</td>
                  <td width='12.5%' rowSpan={2}>油品</td>
                  <td width='12.5%' rowSpan={2}>数量（升）</td>
                  <td width='12.5%' rowSpan={2}>卡内余额（元）</td>
                  <td width='12.5%' rowSpan={2}>经办人</td>
                  <td width='25%' colSpan="2">仪表显示公里（公里）</td>
                </tr>
                <tr>

                  <td width='12.5%' >上次</td>
                  <td width='12.5%' >本次</td>
                </tr>
                <tr height={30}>
                  <td width='12.5%'>{jypz.numberplate}</td>
                  <td width='12.5%'>{jypz.youpin}</td>
                  <td width='12.5%'>{jypz.shuliang}</td>
                  <td width='12.5%'>{jypz.yue}</td>
                  <td width='12.5%'>{jypz.jingbanren}</td>
                  <td width='12.5%'>{jypz.shangciyibiaoshu}</td>
                  <td width='12.5%'>{jypz.benciyibiaoshu}</td>
                </tr>
                <tr height={30}>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                  <td width='12.5%'></td>
                </tr>
              </tbody>
            </table>
            </div>
          </div>
        </Modal>
)
}

export default PrintModal ;
