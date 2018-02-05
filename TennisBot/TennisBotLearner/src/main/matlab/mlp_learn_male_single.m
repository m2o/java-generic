    gender = 'male'
    type = 'single'
    
    train_in_path = sprintf('../../../data/%s/%s/feature/train/all/in',type,gender);
    train_out_path = sprintf('../../../data/%s/%s/feature/train/all/out',type,gender);
    validate_in_path = sprintf('../../../data/%s/%s/feature/validate/all/in',type,gender);
    validate_out_path = sprintf('../../../data/%s/%s/feature/validate/all/out',type,gender);
    test_in_path = sprintf('../../../data/%s/%s/feature/test/all/in',type,gender);
    test_out_path = sprintf('../../../data/%s/%s/feature/test/all/out',type,gender);
    
    output_dir = sprintf('../../../data/%s/%s/matlab/',type,gender)
    
    net = mlp_learn(train_in_path,train_out_path,validate_in_path,validate_out_path,test_in_path,test_out_path,output_dir);