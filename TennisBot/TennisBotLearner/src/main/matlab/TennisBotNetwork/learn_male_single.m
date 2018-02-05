    gender = 'male'
    type = 'single'
    
    train_in_path = sprintf('../../../../data/%s/%s/feature/train/all/in',type,gender);
    train_out_path = sprintf('../../../../data/%s/%s/feature/train/all/out',type,gender);
    
    output_dir = sprintf('../../../../data/%s/%s/matlab/',type,gender);
    
    net = learn(train_in_path,train_out_path, output_dir);