
train_in = load(sprintf('../../../data/%s/%s/feature/feature_train_in','single','male'));
train_out = load(sprintf('../../../data/%s/%s/feature/feature_train_out','single','male'));
test_in = load(sprintf('../../../data/%s/%s/feature/feature_test_in','single','male'));
test_out = load(sprintf('../../../data/%s/%s/feature/feature_test_out','single','male'));

train_in = train_in';
train_out = train_out';
test_in = test_in';
test_out = test_out';

%temp
[x,order]=shuffle(train_in,2);
orderby(train_in,order,2);
orderby(train_out,order,2);
train_in = train_in(:,1:4000);
train_out = train_out(:,1:4000);

size(train_in)
size(train_out)

goal = 0.135;
spread = 1.0;
max_neurons = 100; %50
delta_neurons = 20; %10

net=newrb(train_in, train_out,goal,spread,max_neurons,delta_neurons);

simpleclassOutputs = sim(net, test_in);
plotconfusion(test_out, simpleclassOutputs);

dlmwrite(sprintf('../../../data/%s/%s/matlab/rb_centers.dat','single','male'), net.IW{1}, ' ');
dlmwrite(sprintf('../../../data/%s/%s/matlab/rb_centers_biases.dat','single','male'), net.b{1}, ' ');
dlmwrite(sprintf('../../../data/%s/%s/matlab/rb_weights.dat','single','male'), net.LW{2,1}, ' ');
dlmwrite(sprintf('../../../data/%s/%s/matlab/rb_weights_biases.dat','single','male'), net.b{2}, ' ');
