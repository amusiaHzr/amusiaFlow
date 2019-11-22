# amusiaFlow
一个简单的流式布局：


如何使用：

  step 1：
  allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    
  step 2：
  dependencies {
	        implementation 'com.github.amusiaHzr:amusiaFlow:1.0'
	}
  
  step 3：
  flowView = findViewById(R.id.flow_view);
        simpleFlowAdapter = new SimpleFlowAdapter<DataBean>(this, R.layout.item, datas) {
            @Override
            public void convert(View itemView, Object object, int position) {
                DataBean dataBean = (DataBean) object;
                TextView nameText = itemView.findViewById(R.id.name);
                ImageView logoImage = itemView.findViewById(R.id.logo);
                nameText.setText(dataBean.getName());
                logoImage.setBackgroundResource(dataBean.getLogo());

            }
        };
        
  step 4:
  flowView.setAdapter(simpleFlowAdapter);
