import { Component } from '@angular/core';
import { OrderDto } from '../../model/OrderDto';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../service/order.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-order-service',
  imports: [CommonModule],
  templateUrl: './order-service.component.html',
  styleUrl: './order-service.component.css'
})
export class OrderServiceComponent {
  orderSummary?: OrderDto;
  obj: any;
  total?: any;
  showDialog: boolean = false;

  constructor(private route: ActivatedRoute, private orderService: OrderService, private router: Router) { }

  ngOnInit() {
    const data = this.route.snapshot.queryParams['data'];
    this.obj = JSON.parse(data);
    this.obj.userId = 2; // Hardcoded userId for demo
    this.orderSummary = this.obj;

    this.total = (this.orderSummary?.foodItems ?? []).reduce((accumulator, currentValue) => {
      return accumulator + (currentValue.quantity * currentValue.price);
    }, 0);

  }

  saveOrder() {
    this.orderService.saveOrder(this.orderSummary)
      .subscribe({
        next: () => {
          this.showDialog = true;
        },
        error: (error) => {
          console.error('Failed to save data:', error);
        }
      });
  }

  closeDialog() {
    this.showDialog = false;
    this.router.navigate(['/']);
  }

}
