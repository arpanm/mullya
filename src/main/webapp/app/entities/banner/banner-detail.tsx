import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './banner.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BannerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bannerEntity = useAppSelector(state => state.banner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bannerDetailsHeading">
          <Translate contentKey="mullyaApp.banner.detail.title">Banner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mullyaApp.banner.name">Name</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.name}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="mullyaApp.banner.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.imageUrl}</dd>
          <dt>
            <span id="mobileImageUrl">
              <Translate contentKey="mullyaApp.banner.mobileImageUrl">Mobile Image Url</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.mobileImageUrl}</dd>
          <dt>
            <span id="landingUrl">
              <Translate contentKey="mullyaApp.banner.landingUrl">Landing Url</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.landingUrl}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="mullyaApp.banner.description">Description</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.description}</dd>
          <dt>
            <span id="html">
              <Translate contentKey="mullyaApp.banner.html">Html</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.html}</dd>
          <dt>
            <span id="mobileHtml">
              <Translate contentKey="mullyaApp.banner.mobileHtml">Mobile Html</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.mobileHtml}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mullyaApp.banner.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="mullyaApp.banner.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.startDate}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="mullyaApp.banner.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.endDate}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mullyaApp.banner.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {bannerEntity.createdOn ? <TextFormat value={bannerEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mullyaApp.banner.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mullyaApp.banner.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {bannerEntity.updatedOn ? <TextFormat value={bannerEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mullyaApp.banner.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.updatedBy}</dd>
        </dl>
        <Button tag={Link} to="/banner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banner/${bannerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BannerDetail;
